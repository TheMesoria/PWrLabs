rm *.jar
rm ~/Work/Labs/Lab5Task1V3/jarB/jar/CalendarBean.fxml
rm ~/Work/Labs/Lab5Task1V3/jarB/jar/calendarBean/CalendarBean.class 
cp /home/black/Work/Labs/Lab5Task1V3/build/classes/java/main/calendarBean/* ~/Work/Labs/Lab5Task1V3/jarB/jar/calendarBean/
cp /home/black/Work/Labs/Lab5Task1V3/build/resources/main/* ~/Work/Labs/Lab5Task1V3/jarB/jar/
cd jar
jar cvf JavaBean.jar .
cd ..