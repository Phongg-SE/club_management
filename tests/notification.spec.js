// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 1: In-App Notification Center', () => {

  test('Hiển thị số lượng thông báo chưa đọc và mở danh sách', async ({ page }) => {
    await page.goto('/notification');

    // 1. Kiểm tra badge unread hiển thị đúng số lượng ban đầu (> 0)
    const unreadBadge = page.locator('#unreadBadge');
    await expect(unreadBadge).toBeVisible();
    await expect(unreadBadge).toHaveText('3');

    // 2. Danh sách thông báo có các item hiển thị
    const items = page.locator('.notify-item');
    await expect(items).toHaveCount(4);
  });

  test('Lọc thông báo theo danh mục (TASK, EVENT, FINANCE)', async ({ page }) => {
    await page.goto('/notification');

    // Bấm lọc Task
    await page.click('#filter-task');
    await expect(page.locator('.notify-item')).toHaveCount(1);
    await expect(page.locator('.notify-item').first()).toContainText('Task mới được giao');

    // Bấm lọc Event
    await page.click('#filter-event');
    await expect(page.locator('.notify-item')).toHaveCount(1);
    await expect(page.locator('.notify-item').first()).toContainText('Sự kiện sắp diễn ra');

    // Bấm lọc Finance
    await page.click('#filter-finance');
    await expect(page.locator('.notify-item')).toHaveCount(1);
    await expect(page.locator('.notify-item').first()).toContainText('Giao dịch quỹ đã duyệt');

    // Quay lại Tất cả
    await page.click('#filter-all');
    await expect(page.locator('.notify-item')).toHaveCount(4);
  });

  test('Đánh dấu tất cả đã đọc xóa sạch badge unread', async ({ page }) => {
    await page.goto('/notification');

    // Click nút Đánh dấu tất cả đã đọc
    await page.click('#markAllReadBtn');

    // Badge unread biến mất
    await expect(page.locator('#unreadBadge')).toBeHidden();

    // Tất cả thông báo chuyển sang trạng thái read
    const unreadItems = page.locator('.notify-item.unread');
    await expect(unreadItems).toHaveCount(0);
  });
});
