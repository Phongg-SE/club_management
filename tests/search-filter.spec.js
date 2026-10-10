// @ts-check
const { test, expect } = require('@playwright/test');

test.describe('Module 7: Advanced Multi-criteria Search & Filter', () => {

  test('Lọc danh sách theo Ban chuyên môn', async ({ page }) => {
    await page.goto('/search-filter');

    // Lọc theo Ban Truyền Thông
    await page.selectOption('#deptFilter', 'Ban Truyền Thông');

    // Kết quả trả về 1 thành viên
    await expect(page.locator('#resultCount')).toHaveText('1');
    await expect(page.locator('#tableBody tr')).toHaveCount(1);
    await expect(page.locator('#tableBody')).toContainText('Lê Hoàng Cường');
  });

  test('Tìm kiếm tức thì theo từ khóa tên hoặc MSSV', async ({ page }) => {
    await page.goto('/search-filter');

    // Nhập từ khóa 'Bích'
    await page.fill('#searchInput', 'Bích');
    // Chờ debounce
    await page.waitForTimeout(300);

    await expect(page.locator('#resultCount')).toHaveText('1');
    await expect(page.locator('#tableBody')).toContainText('Trần Thị Bích');
    await expect(page.locator('#tableBody')).toContainText('SE160002');
  });

  test('Hiển thị trạng thái rỗng (empty state) khi không có kết quả phù hợp', async ({ page }) => {
    await page.goto('/search-filter');

    await page.fill('#searchInput', 'MSSV_KHONG_TON_TAI_9999');
    await page.waitForTimeout(300);

    await expect(page.locator('#resultCount')).toHaveText('0');
    await expect(page.locator('#tableBody')).toContainText('Không tìm thấy thành viên nào');
  });
});
