import csv
import sys
import difflib
import dateutil.parser as dparser
from collections import defaultdict
import re
import os
check = []
info = defaultdict(list)

def make_array_single_dimension(l):
    l2 = []

    for x in l:
        if type(x).__name__ == "list":
            l2 += make_array_single_dimension(x)
        else:
            l2.append(x)
    return l2

trig = 0
count = 2
a = os.listdir()
while True:
    if trig == 0:
        q = os.listdir()

        lis = []
        lis.extend(a)
        if(len(q) == count):
            continue
        else:
            trig = 1
            count +=1
            for i in os.listdir():
                if i not in a:
                    new_dir =  (i)
                    # print(new_dir)
                else:
                    pass
            for j in a:
                if j in os.listdir():
                    print("J:-------- " + str(j))
            break
# def getMinDate(date1, date2):
#     if date1 > date2:
#         return date2
#     else:
#         return date1

# def getMaxDate(date1, date2):
#     if date1 < date2:
#         return date2
#     else:
#         return date1

if trig == 1:
    file = open(str(new_dir), 'r')
    Address_keys = ['Floor','Tower','Towers', 'Andhra Pradesh', 'Arunachal Pradesh', 'Assam', 'Bihar', 'Goa', 'Gujarat', 'Haryana', 'Himachal Pradesh', 'Jammu & Kashmir', 'Karnataka', 'Kerala', 'Madhya Pradesh', 'Maharashtra', 'Manipur', 'Meghalaya', 'Mizoram', 'Nagaland', 'Orissa', 'Punjab', 'Rajasthan', 'Sikkim', 'Tamil Nadu', 'Tripura', 'Uttar Pradesh', 'West Bengal', 'Chhattisgarh', 'Uttarakhand', 'Jharkhand', 'Telangana – Hyderabad', 'We also have 7 union territories', 'Andaman and Nicobar Islands', 'Chandigarh', 'New Delhi', 'Dadra and Nagar Haveli', 'Daman and Diu', 'Lakshadweep', 'Pondicherry ', 'Flat', '#', 'Layout', 'Cross','Stage','Sector']
    for i in file:
        i = i.lower()
        # print(i)
        m=i.find("address:")
        if(m!=-1):
            q = i[8:]
            info["address"].append(q)
        for j in Address_keys:
            m=i.find("address:")
            if(m!=-1):
                continue
            j = j.lower()
            if i.find(j) == -1:
                pass
            else:
                check.append(i[:])
            check = list(set(check))
    info["address"].append(check)
    add = make_array_single_dimension(info["address"])
    print("Address: ",add)



    nameline = []
    text0 = []
    dates = []

    f = open(str(new_dir),"r")
    for a in f:
        a = a.replace(","," ")
        a = a.replace("\n","")
        a = a.replace(":"," ")
        a = a.replace("[", "")
        a = a.replace("]", "")
        text0.append(a)
    # print(text0)


    with open('namedb.csv', 'r') as f:
        reader = csv.reader(f)
        newlist = list(reader)
    newlist = sum(newlist, [])

    # Searching for Name and finding closest name in database
    try:
        for x in text0:
            for y in x.split():
                if y in newlist:
                # if(difflib.get_close_matches(y.upper(), newlist)):
                    nameline.append(y)
                    break
    except Exception as ex:
        pass
    s=str(nameline)
    print(s)

    import datefinder

    for i in text0:
        matches = list(datefinder.find_dates(i))

        if len(matches) > 0:
            date = matches[0]
            dates.append(date)

            # print("Dates:----"+ str(dates))
        else:
            pass
    print("Date:", dates)
    # minDate = None
    # maxDate = None

    # for i in range(len(dates)):
    #     print(i)
    #     if i < len(dates):
    #         mini = dates[i]
    #         minDate = getMinDate(minDate, mini)
    #         print(minDate+"--------->>>>>>>")
    for i in text0:
        a = i.find("+")
        b = i.find("-")
        if a != -1:
            bloodgroup = i[a-1:a+1]
        elif b != -1:
            bloodgroup = i[b-1:b+1]
        elif a == -1:
            pass
        else:
            pass
            
        # print(str(a)+"--------------")

    # for i in text0:
    #     if(len(i) == 17):
    #         chassisno = i
    #     else:
    #         chassisno = None
    # print(chassisno)
    trig = 0




import json
# my_list = ["a","b","c"]
# my_jsonstring = json.dumps(my_list)

data = {
    "DATA": {
        "name": nameline,
        "address": add,
        "Dates": str(dates),
        "Blood Group": bloodgroup
    }
}
with open("data_file.json", "w") as write_file:
    json.dump(data, write_file)

