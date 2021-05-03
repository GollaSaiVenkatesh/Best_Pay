import matplotlib.pyplot as plt
import cv2
import base64
from PIL import Image
import numpy as np
import io
import csv
import re
from os.path import dirname, join

def construct_graph(string_gen,datesamazon,flipkart,amazon):

    f=(string_gen in flipkart.keys())
    a=(string_gen in amazon.keys())
    def removecomma(ar):
        for i in range(0,len(ar)):
            if(re.search(ar[i],"unavailable[\s]*$")):
                ar[i]=None
                continue
            ar[i]=float(ar[i].replace(",",""))
        return ar
    if(f):
        print("-----------------------------------------------------------Flipkart")
        print("Item found on flipkart")    
        flip=flipkart[string_gen][4:]  
        flip=removecomma(flip)
    else:
        print("Item Not Found on Flipkart")
    if(a):
        print("-----------------------------------------------------------Amazon")
        print("Item found on Amazon ")
        ama=amazon[string_gen][4:]
        ama=removecomma(ama)
    else:
        print("Item Not Found on Amazon")

    x=datesamazon[5:]

    fig = plt.figure()
    fig.set_figwidth(2*len(x))
    fig.set_figheight(len(x))
    if(f):
        x1 = x
        y1 = flip    
        plt.plot(x1, y1, label = "flipkart")
    if(a):  
          # line 2 points 
        x2 = x
        y2 = ama     
        plt.plot(x2, y2, label = "amazon")
    if(a or f):    
        plt.legend() 
        plt.xlabel('Dates') 
        plt.ylabel('Prices') 
        plt.title(string_gen)
        plt.show()

    fig.canvas.draw()
    img = np.fromstring(fig.canvas.tostring_rgb(),dtype=np.uint8,sep='')
    img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,))
    img = cv2.cvtColor(img,cv2.COLOR_RGB2BGR)
    pil_im = Image.fromarray(img)
    buff = io.BytesIO()
    pil_im.save(buff,format="PNG")
    img_str = base64.b64encode(buff.getvalue())
    return ""+str(img_str,'utf-8')

def submain(string_gen):
    string_gen=string_gen.lower().replace(" ","")
    flipkart={}
    amazon={}
    datesamazon=[]
    datesflipkart=[]
    flipkartfile = join(dirname(__file__), "flipkart.csv")
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
    return construct_graph(string_gen,datesamazon,flipkart,amazon)

def main(string_gen):
    return submain(string_gen)
