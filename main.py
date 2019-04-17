from faker import Faker
import csv
import random
import json  # To create a json file
from random import randint  # For student id

fake = Faker()

def input_data(x):
    subjectList = ['Biochemistry', 'Molecular Biology', 'Chemistry', 'Biology', 'Computer Science', 'Data Analytics',
                   'Enviornmental Science', 'Math', 'Physics', 'Software Engineering']
    titleList = ['Intro to ', 'Intermediate ', 'Advanced ']


    # dictionary
    student_data = {}
    for i in range(1, 1001):
        sub = random.choice(subjectList)
        className = sub[:3].upper()
        student_data[i] = {}
        student_data[i]=[i,fake.name(),fake.email(),fake.password(),randint(1,1000),
                         random.choice(titleList)+sub, randint(12345678910111, 90000000000000),
                         sub, randint(1, 1000), className+ str(randint(100, 400)),randint(1, 10000),randint(0,1),
                         randint(1, 1000),randint(0,1)]

    with open('Textbook_Exchange.csv', 'w') as csv_file:
        writer = csv.writer(csv_file)
        for key, value in student_data.items():
            writer.writerow(value)
    csv_file.close()

def main():
    # Enter number of students
    number_of_students = 10  # For the above task make this 100
    input_data(number_of_students)
main()