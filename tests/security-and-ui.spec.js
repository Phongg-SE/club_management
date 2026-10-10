// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 9: UI Enhancements & Security Hardening', () => {

  test('Chuyển đổi giao diện sáng / tối (Dark Mode toggle)', async ({ page }) => {
    await page.goto('/');

    const themeBtn = page.locator('#themeToggleBtn');
    await expect(themeBtn).toContainText('Chế độ tối');

    // Chuyển sang Dark mode
    await themeBtn.click();
    await expect(page.locator('body')).toHaveAttribute('data-theme', 'dark');
    await expect(themeBtn).toContainText('Chế độ sáng');

    // Toast notification xuất hiện
    const toast = page.locator('#toast');
    await expect(toast).toBeVisible();
    await expect(toast).toContainText('chế độ tối');
  });

  test('Kiểm tra chính sách mật khẩu mạnh (Password Strength Checker)', async ({ page }) => {
    await page.goto('/security');

    const input = page.locator('#testPasswordInput');
    const feedback = page.locator('#passwordFeedback');

    // 1. Mật khẩu yếu
    await input.fill('12345');
    await expect(feedback).toContainText('Mật khẩu yếu');

    // 2. Mật khẩu mạnh (đủ chữ hoa, số, ký tự đặc biệt, 8+ ký tự)
    await input.fill('ClubLeader@2026!');
    await expect(feedback).toContainText('Mật khẩu mạnh');
  });

  test('Bảo vệ CSRF: Chấp thuận token hợp lệ và từ chối token giả mạo', async ({ page }) => {
    await page.goto('/security');

    // 1. Gửi với token giả mạo
    await page.click('#btnSubmitWithInvalidCsrf');
    const csrfResult = page.locator('#csrfResult');
    await expect(csrfResult).toBeVisible();
    await expect(csrfResult).toContainText('CSRF Token không hợp lệ');

    // 2. Gửi với token hợp lệ
    await page.click('#btnSubmitWithValidCsrf');
    await expect(csrfResult).toContainText('Thao tác bảo mật thành công');
  });

  test('Phòng chống tấn công XSS (Cross-Site Scripting)', async ({ page }) => {
    await page.goto('/security');

    // Bấm nút Sanitize & Render
    await page.click('#btnTestXssSanitize');

    // Output hiển thị nội dung text an toàn chứ không thực thi script
    const output = page.locator('#xssOutput');
    await expect(output).toContainText('alert');
    // Kiểm tra không có thẻ <script> nào được chèn thực tế vào DOM
    const scriptTag = page.locator('#xssOutput script');
    await expect(scriptTag).toHaveCount(0);
  });
});
