rm jar/*.jar
rm ~/Work/Labs/Lab5Task1V3/jarB/jar/CalendarBean.fxml
rm ~/Work/Labs/Lab5Task1V3/jarB/jar/calendarBean/CalendarBean.class 
cp /home/black/Work/Labs/Lab5Task1V3/out/production/classes/calendarBean/* ~/Work/Labs/Lab5Task1V3/jarB/jar/calendarBean/
cp /home/black/Work/Labs/Lab5Task1V3/out/production/resources/* ~/Work/Labs/Lab5Task1V3/jarB/jar/
cd jar
jar cvf calendarBean.jar .
cd ..