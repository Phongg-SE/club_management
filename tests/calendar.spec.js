// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 4: Calendar & Timeline View', () => {

  test('Hiển thị lưới lịch tháng và các ngày trong tuần', async ({ page }) => {
    await page.goto('/calendar');

    // Kiểm tra đủ 7 cột thứ trong tuần
    await expect(page.locator('.day-header')).toHaveCount(7);
    await expect(page.locator('.day-cell')).toHaveCount(7);
  });

  test('Hiển thị sự kiện và deadline task trực quan trên ô lịch', async ({ page }) => {
    await page.goto('/calendar');

    // Task ngày hôm nay
    const todayTask = page.locator('#item-today-task');
    await expect(todayTask).toBeVisible();
    await expect(todayTask).toContainText('Nộp kịch bản Gala');

    // Sự kiện ngày mai
    const galaEvent = page.locator('#item-gala-event');
    await expect(galaEvent).toBeVisible();
    await expect(galaEvent).toContainText('Gala Tân Sinh Viên');
  });

  test('Bấm vào sự kiện hiển thị modal thông tin chi tiết', async ({ page }) => {
    await page.goto('/calendar');

    await page.click('#item-gala-event');

    const modal = page.locator('#detailModal');
    await expect(modal).toBeVisible();
    await expect(page.locator('#modalTitle')).toContainText('Gala Chào Tân Sinh Viên');
    await expect(page.locator('#modalDesc')).toContainText('Hội trường lớn Alpha');

    // Đóng modal
    await page.click('#detailModal button');
    await expect(modal).toBeHidden();
  });
});
