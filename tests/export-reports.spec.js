// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 8: Data Export & Reports (CSV/Excel)', () => {

  test('Tải xuống file CSV danh sách thành viên thành công với đầy đủ cột', async ({ page }) => {
    await page.goto('/export-reports');

    // Bắt sự kiện tải file
    const downloadPromise = page.waitForEvent('download');
    await page.click('#btnDownloadMembersCsv');
    const download = await downloadPromise;

    // Kiểm tra tên file tải về
    expect(download.suggestedFilename()).toBe('club_members_export.csv');
  });

  test('Kiểm tra nội dung dữ liệu API xuất CSV thành viên', async ({ request }) => {
    const response = await request.get('/api/export/members');
    expect(response.status()).toBe(200);

    const headers = response.headers();
    expect(headers['content-type']).toContain('text/csv');

    const bodyText = await response.text();
    // Chứa header CSV chuẩn
    expect(bodyText).toContain('ID,Full Name,Student ID,Email,Role,Department,Status');
    // Chứa thông tin các thành viên
    expect(bodyText).toContain('Nguyễn Văn An');
    expect(bodyText).toContain('SE160001');
  });
});
