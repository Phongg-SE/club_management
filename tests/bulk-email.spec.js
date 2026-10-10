// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 6: Bulk Email Notification Dispatcher', () => {

  test('Áp dụng mẫu email có sẵn (Template) tự động điền tiêu đề và nội dung', async ({ page }) => {
    await page.goto('/bulk-email');

    // Chọn mẫu họp CLB
    await page.selectOption('#emailTemplate', 'MEETING');

    // Tiêu đề và nội dung được tự động điền
    await expect(page.locator('#emailSubject')).toHaveValue('[CLB Thông Báo] Lịch Họp Định Kỳ Tuần Này');
    await expect(page.locator('#emailContent')).toHaveValue(/lịch họp CLB/);
  });

  test('Mở popup xác nhận trước khi gửi và hiển thị thông báo thành công', async ({ page }) => {
    await page.goto('/bulk-email');

    // Chọn template nhắc nhở đóng quỹ
    await page.selectOption('#emailTemplate', 'FEE');

    // Bấm nút gửi email
    await page.click('#btnSendMail');

    // Modal xác nhận xuất hiện
    const modal = page.locator('#confirmModal');
    await expect(modal).toBeVisible();

    // Bấm xác nhận gửi
    await page.click('#btnConfirmSend');

    // Modal đóng lại và alert thành công xuất hiện
    await expect(modal).toBeHidden();
    const alert = page.locator('#emailAlert');
    await expect(alert).toBeVisible();
    await expect(alert).toContainText('gửi thành công');
  });
});
