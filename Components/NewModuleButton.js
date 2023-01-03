import React, { useEffect, useState } from 'react';
import {NativeModules, Button ,Image} from 'react-native';

const { ImagePickerModule  , BatteryStatusModule} = NativeModules;

const NewModuleButton = () => {

  const [val,setVal] = useState("");

  console.log(BatteryStatusModule);



    // CalendarModule.createCalendarEvent("Sahad","Kerela",(res)=>{console.log(res)});

    const onPress = async() => {
      try{
        const x =  await ImagePickerModule.pickImage();
        if(x){
          console.log(x);
          setVal(x);
        }
      }
      catch(e){
        console.log(e);
      }
       
      };






      const getBatteryLevel = async() => {
        try {
        const x = await BatteryStatusModule.getBatteryStatus();
          console.log(x);
        } catch (error) {
          console.log("WOw");
        }
      }

  return (
    <>
    
    {val ? <><Image source={{uri:val}} style={{height:100,width:100}} resizeMode={"cover"} /></>:<></>}
    <Button
      title="Gallery Module"
      color="#841584"
      onPress={onPress}
    />
     <Button
      title="Battery Module"
      color="#841584"
      onPress={getBatteryLevel}
    />

    </>
  );
};

export default NewModuleButton;