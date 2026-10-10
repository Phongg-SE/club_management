const http = require('http');
const fs = require('fs');
const path = require('path');
const url = require('url');

const PORT = process.env.PORT || 3000;

// In-memory mock data
let mockNotifications = [
  { id: 1, title: 'Task mới được giao', message: 'Bạn được giao task: Thiết kế banner Hội Thao CLB', type: 'TASK', is_read: false, created_at: '2026-10-10 09:00' },
  { id: 2, title: 'Sự kiện sắp diễn ra', message: 'Sự kiện Gala Chào Tân Sinh Viên diễn ra vào ngày mai', type: 'EVENT', is_read: false, created_at: '2026-10-10 08:30' },
  { id: 3, title: 'Giao dịch quỹ đã duyệt', message: 'Chi phí mua nước uống 250,000 VND đã được duyệt', type: 'FINANCE', is_read: true, created_at: '2026-10-09 16:45' },
  { id: 4, title: 'Đơn đăng ký mới', message: 'Nguyễn Văn B vừa nộp đơn đăng ký vào CLB Âm Nhạc', type: 'MEMBER', is_read: false, created_at: '2026-10-09 14:10' }
];

let mockComments = [
  { id: 1, author: 'Trần Văn Nam', content: 'Sự kiện lần này chuẩn bị chu đáo quá mọi người ơi!', created_at: '2026-10-09 10:15', parent_id: null },
  { id: 2, author: 'Lê Thị Hoa', content: 'Đồng ý, hy vọng hội trường đủ chỗ cho tất cả các bạn tân sinh viên.', created_at: '2026-10-09 10:30', parent_id: 1 }
];

let mockFeedbacks = [
  { id: 1, event_id: 101, user_id: 1, rating: 5, comment: 'Sự kiện rất ý nghĩa và sôi động!', created_at: '2026-10-08 20:00' },
  { id: 2, event_id: 101, user_id: 2, rating: 4, comment: 'Chương trình hơi dài một chút nhưng tiết mục hay.', created_at: '2026-10-08 20:15' }
];

const mockMembers = [
  { id: 1, full_name: 'Nguyễn Văn An', student_id: 'SE160001', email: 'an.nv@fpt.edu.vn', role: 'Leader', department: 'Ban Chủ Nhiệm', status: 'Active' },
  { id: 2, full_name: 'Trần Thị Bích', student_id: 'SE160002', email: 'bich.tt@fpt.edu.vn', role: 'Vice Leader', department: 'Ban Chuyên Môn', status: 'Active' },
  { id: 3, full_name: 'Lê Hoàng Cường', student_id: 'SE160003', email: 'cuong.lh@fpt.edu.vn', role: 'Member', department: 'Ban Truyền Thông', status: 'Active' },
  { id: 4, full_name: 'Phạm Thu Dung', student_id: 'SE160004', email: 'dung.pt@fpt.edu.vn', role: 'Member', department: 'Ban Hậu Cần', status: 'Inactive' }
];

const validCsrfTokens = new Set(['valid-test-csrf-token-12345']);

// Helper to escape HTML to prevent XSS
function escapeHtml(unsafe) {
  if (typeof unsafe !== 'string') return '';
  return unsafe
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}

const server = http.createServer((req, res) => {
  const parsedUrl = url.parse(req.url, true);
  const pathname = parsedUrl.pathname;

  // CORS headers
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type, X-CSRF-Token');

  if (req.method === 'OPTIONS') {
    res.writeHead(204);
    res.end();
    return;
  }

  // API: Get notifications
  if (pathname === '/api/notifications' && req.method === 'GET') {
    res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
    res.end(JSON.stringify(mockNotifications));
    return;
  }

  // API: Mark all notifications read
  if (pathname === '/api/notifications/mark-all-read' && req.method === 'POST') {
    mockNotifications = mockNotifications.map(n => ({ ...n, is_read: true }));
    res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
    res.end(JSON.stringify({ success: true, unreadCount: 0 }));
    return;
  }

  // API: Get comments
  if (pathname === '/api/comments' && req.method === 'GET') {
    res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
    res.end(JSON.stringify(mockComments));
    return;
  }

  // API: Post new comment
  if (pathname === '/api/comments' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => { body += chunk; });
    req.on('end', () => {
      try {
        const data = JSON.parse(body);
        if (!data.content || !data.content.trim()) {
          res.writeHead(400, { 'Content-Type': 'application/json; charset=utf-8' });
          res.end(JSON.stringify({ error: 'Nội dung bình luận không được để trống' }));
          return;
        }

        const newComment = {
          id: mockComments.length + 1,
          author: data.author ? escapeHtml(data.author) : 'Bạn (User hiện tại)',
          content: escapeHtml(data.content.trim()),
          created_at: new Date().toISOString().replace('T', ' ').substring(0, 16),
          parent_id: data.parent_id || null
        };
        mockComments.push(newComment);
        res.writeHead(201, { 'Content-Type': 'application/json; charset=utf-8' });
        res.end(JSON.stringify({ success: true, comment: newComment }));
      } catch (err) {
        res.writeHead(400, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ error: 'Invalid JSON' }));
      }
    });
    return;
  }

  // API: Event feedback submission
  if (pathname === '/api/feedback' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => { body += chunk; });
    req.on('end', () => {
      try {
        const data = JSON.parse(body);
        const eventId = parseInt(data.event_id, 10);
        const userId = parseInt(data.user_id, 10) || 1;
        const rating = parseInt(data.rating, 10);
        const comment = data.comment ? escapeHtml(data.comment.trim()) : '';

        if (!rating || rating < 1 || rating > 5) {
          res.writeHead(400, { 'Content-Type': 'application/json; charset=utf-8' });
          res.end(JSON.stringify({ error: 'Đánh giá phải từ 1 đến 5 sao' }));
          return;
        }

        // BR: Only 1 feedback per user per event
        const alreadySubmitted = mockFeedbacks.some(f => f.event_id === eventId && f.user_id === userId);
        if (alreadySubmitted) {
          res.writeHead(409, { 'Content-Type': 'application/json; charset=utf-8' });
          res.end(JSON.stringify({ error: 'Bạn đã đánh giá sự kiện này trước đó rồi!' }));
          return;
        }

        const newFeedback = {
          id: mockFeedbacks.length + 1,
          event_id: eventId,
          user_id: userId,
          rating,
          comment,
          created_at: new Date().toISOString().replace('T', ' ').substring(0, 16)
        };
        mockFeedbacks.push(newFeedback);

        // Calculate new average
        const eventFeedbacks = mockFeedbacks.filter(f => f.event_id === eventId);
        const avgRating = (eventFeedbacks.reduce((sum, f) => sum + f.rating, 0) / eventFeedbacks.length).toFixed(1);

        res.writeHead(201, { 'Content-Type': 'application/json; charset=utf-8' });
        res.end(JSON.stringify({ success: true, feedback: newFeedback, averageRating: avgRating, totalFeedbacks: eventFeedbacks.length }));
      } catch (err) {
        res.writeHead(400, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ error: 'Invalid JSON' }));
      }
    });
    return;
  }

  // API: Export Members to CSV
  if (pathname === '/api/export/members' && req.method === 'GET') {
    const csvHeader = 'ID,Full Name,Student ID,Email,Role,Department,Status\r\n';
    const csvRows = mockMembers.map(m => 
      `"${m.id}","${m.full_name}","${m.student_id}","${m.email}","${m.role}","${m.department}","${m.status}"`
    ).join('\r\n');
    res.writeHead(200, {
      'Content-Type': 'text/csv; charset=utf-8',
      'Content-Disposition': 'attachment; filename="club_members_export.csv"'
    });
    res.end('\uFEFF' + csvHeader + csvRows);
    return;
  }

  // API: Sensitive action with CSRF protection
  if (pathname === '/api/security/sensitive-action' && req.method === 'POST') {
    let body = '';
    req.on('data', chunk => { body += chunk; });
    req.on('end', () => {
      const csrfToken = req.headers['x-csrf-token'];
      if (!csrfToken || !validCsrfTokens.has(csrfToken)) {
        res.writeHead(403, { 'Content-Type': 'application/json; charset=utf-8' });
        res.end(JSON.stringify({ success: false, error: 'CSRF Token không hợp lệ hoặc đã hết hạn! Yêu cầu bị từ chối.' }));
        return;
      }
      res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
      res.end(JSON.stringify({ success: true, message: 'Thao tác bảo mật thành công!' }));
    });
    return;
  }

  // Web Pages
  const pageMap = {
    '/': 'index.html',
    '/notification': 'notification.html',
    '/dashboard': 'dashboard.html',
    '/comments': 'comments.html',
    '/calendar': 'calendar.html',
    '/feedback': 'feedback.html',
    '/bulk-email': 'bulk-email.html',
    '/search-filter': 'search-filter.html',
    '/export-reports': 'export-reports.html',
    '/security': 'security.html'
  };

  if (pageMap[pathname]) {
    const filePath = path.join(__dirname, 'views', pageMap[pathname]);
    if (fs.existsSync(filePath)) {
      res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
      fs.createReadStream(filePath).pipe(res);
      return;
    }
  }

  // 404
  res.writeHead(404, { 'Content-Type': 'text/plain; charset=utf-8' });
  res.end('404 Not Found');
});

server.listen(PORT, () => {
  console.log(`Club Management Improvement Test Server running on http://localhost:${PORT}`);
});
