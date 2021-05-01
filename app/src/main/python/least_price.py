import matplotlib.pyplot as plt
import cv2
import base64
from PIL import Image
import numpy as np
import io
import csv
import re
from os.path import dirname, join

def submain(string_gen):
    string_gen=string_gen.lower().replace(" ","")
    flipkart={}
    amazon={}
    datesamazon=[]
    datesflipkart=[]
    flipkartfile = join(dirname(__file__), "flipkart.csv")
    def removecomma(ar):
        mini=float('inf')
        for i in range(0,len(ar)):
            if(re.search(ar[i],"unavailable[\s]*$")):
                ar[i]=None
                continue
            ar[i]=float(ar[i].replace(",",""))
            if(ar[i]!=None):
                mini=min(mini,(ar[i]))
                
        return ar,mini
    with open(flipkartfile,"r",encoding="cp1252") as e:
        reader=csv.reader(e)

        row=next(reader)
        datesflipkart=row
        for row in reader:
            flipkart[row[0].lower().replace(" ","")]=row[1:]

    amazonfile = join(dirname(__file__), "amazon.csv")
    with open(amazonfile,"r",encoding="cp1252") as e:
        reader=csv.reader(e)
        row=next(reader)
        datesamazon=row
        for row in reader:
            amazon[row[0].lower().replace(" ","")]=row[1:]
    f=(string_gen in flipkart.keys())
    a=(string_gen in amazon.keys())
    if(f and a):
        flip=flipkart[string_gen][4:]  
        flip,minif=removecomma(flip)
        ama=amazon[string_gen][4:]
        ama,minia=removecomma(ama)
        return min(minif,minia)
    if(f):
        flip=flipkart[string_gen][4:]  
        flip,minif=removecomma(flip)
        return minif
    if(a):
        ama=amazon[string_gen][4:]
        ama,minia=removecomma(ama)
        return minia
        
def main(string_gen):
    return submain(string_gen)
