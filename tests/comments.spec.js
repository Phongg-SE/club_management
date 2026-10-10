// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 3: Post & Event Comments Discussion', () => {

  test('Gửi bình luận mới thành công và cập nhật số lượng', async ({ page }) => {
    await page.goto('/comments');

    const initialCountText = await page.locator('#commentCount').textContent();
    const initialCount = parseInt(initialCountText || '0', 10);

    // Nhập nội dung bình luận
    await page.fill('#commentInput', 'Rất mong chờ sự kiện lần này của CLB!');
    await page.click('#submitCommentBtn');

    // Số lượng tăng lên 1
    await expect(page.locator('#commentCount')).toHaveText(String(initialCount + 1));

    // Bình luận mới xuất hiện trong danh sách
    await expect(page.locator('#commentsList')).toContainText('Rất mong chờ sự kiện lần này của CLB!');
    await expect(page.locator('#commentInput')).toHaveValue('');
  });

  test('Báo lỗi validation khi để trống nội dung bình luận', async ({ page }) => {
    await page.goto('/comments');

    await page.fill('#commentInput', '   ');
    await page.click('#submitCommentBtn');

    // Hiển thị thông báo lỗi
    await expect(page.locator('#commentError')).toBeVisible();
    await expect(page.locator('#commentError')).toContainText('Vui lòng nhập nội dung');
  });

  test('Tính năng trả lời (reply) tag tên người bình luận trước', async ({ page }) => {
    await page.goto('/comments');

    // Click nút Trả lời ở comment đầu tiên
    await page.locator('.reply-btn').first().click();

    // Input được tự động focus và điền tag @Tên
    await expect(page.locator('#commentInput')).toHaveValue(/@/);
  });
});
