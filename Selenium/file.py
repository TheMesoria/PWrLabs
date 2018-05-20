from selenium import webdriver
from selenium.webdriver.support.wait import WebDriverWait

# create a new Firefox session
driver = webdriver.Firefox()
driver.implicitly_wait(30)
# driver.maximize_window()

# navigate to the application home page
driver.get('https://google.com')
driver.implicitly_wait(30)
driver.get('https://bing.com')
driver.implicitly_wait(30)
driver.get('https://nokia.com')
driver.implicitly_wait(30)

# get the search textbox
search_field = driver.find_element_by_name('q')
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