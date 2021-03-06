import random

STUDENTS_COUNT = 10000

students = [(i, 0) for i in range(STUDENTS_COUNT)]

def rate(a, b, c, d):
    s = a + b + c + d
    if s < 3:
        return "3"
    elif s < 6:
        return "2"
    elif s < 9:
        return "1"
    else:
        return "0"

def randStudent():
    return random.randint(0, len(students) - 1)

def getProject():
    _students = []
    for i in range(5):
        if len(students) < 5:
            return []
        student = randStudent()
        (index, projects) = students[student]
        while index in _students:
            student = randStudent()
            (index, projects) = students[student]
        _students.append(index)
        students[student] = (index, projects + 1)
        if projects + 1 == 5:
            del students[student]
    return _students

f = open('data.sql', 'w')

projects = 0
project = getProject()
while len(project) > 0:
    for i in project:
        a = random.randint(0, 3)
        b = random.randint(0, 3)
        c = random.randint(0, 3)
        d = random.randint(0, 3)
        score = rate(a, b, c, d)
        f.write("INSERT INTO public.user_competency (project_id, user_id, competencies, score, created) VALUES (%i, %i, '%i,%i,%i,%i', '%s', current_timestamp);\n" % (projects, i, a, b, c, d, score))
    projects = projects + 1
    project = getProject()
print projects
f.close()