import matplotlib.pyplot as plt
import cv2
import base64
from PIL import Image
import numpy as np
import io
import csv
from os.path import dirname, join

def main(a,b):
    fig = plt.figure()
    x= [1,2,3]
    y= [2,4,1]
    ay = fig.add_subplot(1,1,1)
    ay.plot(x,y)
    ay.set_ylabel('Y', color='blue')
    ay.set_xlabel('X', color='red')
    fig.canvas.draw()
    img = np.fromstring(fig.canvas.tostring_rgb(),dtype=np.uint8,sep='')
    img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,))
    img = cv2.cvtColor(img,cv2.COLOR_RGB2BGR)
    pil_im = Image.fromarray(img)
    buff = io.BytesIO()
    pil_im.save(buff,format="PNG")
    img_str = base64.b64encode(buff.getvalue())
    return ""+str(img_str,'utf-8')
    
    
