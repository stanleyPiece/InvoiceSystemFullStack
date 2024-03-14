import React from "react";

export function FlashMessage({theme, text}) {
  return <div className={"alert alert-" + theme}>{text}</div>;
} 

export function FlashMessageOK({ theme, text, onClose }) {
  return (
  <div className={"alert alert-" + theme} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
    <div>{text}</div>
    <div><button className="btn btn-sm btn-primary ms-2" onClick={onClose}>OK</button></div>
    </div>  
  )
}


