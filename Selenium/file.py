from selenium import webdriver
from selenium.webdriver.support.wait import WebDriverWait

# create a new Firefox session
driver = webdriver.Firefox()
driver.implicitly_wait(30)
# driver.maximize_window()

# navigate to the application home page
driver.set_page_load_timeout(5)
driver.get('http://facebook.com')
assert "Facebook" in driver.title
driver.find_element_by_id('email').send_keys("Selenium Webdriver")
driver.find_element_by_name('pass').send_keys("python")
driver.implicitly_wait(5)
driver.find_element_by_id('loginbutton').click()

title = driver.title

driver.get_screenshot_as_file('test.png')
driver.close()


# get the search textbox
# search_field = driver.find_element_by_name('q')
# search_field.clear()
# # enter search keyword and submit
# search_field.send_keys('t-shirt')
# search_field.submit()
#
# #en = driver.find_element_by_id('search')
# #en.submit()
#
# element = WebDriverWait(driver, 30).until(
#     lambda x: x.find_elements_by_xpath("//p[@class='toolbar-amount']"))
#
#
# # get all the anchor elements which have product names displayed
# # currently on result page using find_elements_by_xpath method