// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 2: Club Leader Analytics Dashboard', () => {

  test('Hiển thị đầy đủ các chỉ số KPI hoạt động', async ({ page }) => {
    await page.goto('/dashboard');

    // Kiểm tra các giá trị KPI cốt lõi
    await expect(page.locator('#val-members')).toHaveText('48');
    await expect(page.locator('#val-tasks')).toHaveText('25');
    await expect(page.locator('#val-events')).toHaveText('6');
    await expect(page.locator('#val-funds')).toContainText('14,250,000');
  });

  test('Hiển thị tỷ lệ tiến độ hoàn thành công việc (Task Completion)', async ({ page }) => {
    await page.goto('/dashboard');

    // Kiểm tra tỷ lệ phần trăm các trạng thái task
    await expect(page.locator('#rate-done')).toHaveText('68%');
    await expect(page.locator('#rate-inprogress')).toHaveText('22%');
    await expect(page.locator('#rate-overdue')).toHaveText('10%');
  });

  test('Tổng quan thu chi và số dư quỹ chính xác', async ({ page }) => {
    await page.goto('/dashboard');

    await expect(page.locator('#sum-income')).toContainText('22,000,000');
    await expect(page.locator('#sum-expense')).toContainText('7,750,000');
    await expect(page.locator('#sum-surplus')).toContainText('14,250,000');
  });
});
