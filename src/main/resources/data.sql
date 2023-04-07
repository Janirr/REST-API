    INSERT INTO student(advanced, name, subject, surname, type_of_school, year_of_school) VALUES (false,"Jakub","Matematyka","Królewicz","Studia",3);

    INSERT INTO reservation(day, end_hour, start_hour, student_id) VALUES (CURRENT_DATE, CURRENT_TIME(), CURRENT_TIME() + 1, 1);
    INSERT INTO reservation(day, end_hour, start_hour, student_id) VALUES (CURRENT_DATE, CURRENT_TIME(), CURRENT_TIME() + 1, 1);
    INSERT INTO reservation(day, end_hour, start_hour, student_id) VALUES (CURRENT_DATE, CURRENT_TIME(), CURRENT_TIME() + 1, 1);

