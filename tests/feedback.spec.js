// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 5: Event Feedback & Rating System', () => {

  test('Chọn sao, nhập cảm nhận và gửi đánh giá thành công', async ({ page }) => {
    await page.goto('/feedback');

    // Click chọn 4 sao
    await page.locator('.star[data-rating="4"]').click();
    await expect(page.locator('#ratingInput')).toHaveValue('4');

    // Nhập nội dung góp ý
    await page.fill('#feedbackComment', 'Chương trình rất hay, diễn giả chia sẻ thực tế và nhiệt tình!');
    await page.click('#btnSubmitFeedback');

    // Thông báo thành công
    const alert = page.locator('#alertMsg');
    await expect(alert).toBeVisible();
    await expect(alert).toContainText('thành công');

    // Nút gửi bị vô hiệu hóa để chống submit nhiều lần
    await expect(page.locator('#btnSubmitFeedback')).toBeDisabled();
  });

  test('Business Rule: Chặn gửi đánh giá trùng lặp lần thứ hai từ cùng một user', async ({ page }) => {
    // Gọi API trực tiếp gửi lại đánh giá trùng lặp của user đã submit
    const response = await page.request.post('/api/feedback', {
      data: {
        event_id: 101,
        user_id: 1, // User 1 đã có trong mockFeedbacks
        rating: 5,
        comment: 'Đánh giá lặp lại'
      }
    });

    // Mong đợi HTTP 409 Conflict
    expect(response.status()).toBe(409);
    const body = await response.json();
    expect(body.error).toContain('Bạn đã đánh giá sự kiện này trước đó rồi');
  });
});
