import React from "react";

import blobT2 from "../images/blob-x-template-2.png";
import blobT3 from "../images/blob-x-template-3.png";
import blobT500_2 from "../images/blob-x-template-p-500-2.png";
import blobT500 from "../images/blob-x-template-p-500.png";
import blobT800_2 from "../images/blob-x-template-p-800-2.png";
import blobT800 from "../images/blob-x-template-p-800.png";
import blobT1080_2 from "../images/blob-x-template-p-1080-2.png";
import blobT1080 from "../images/blob-x-template-p-1080.png";
import blobT from "../images/blob-x-template.png";

function FullBlobs() {
  return (
    <div className="bg-blobs-wrapper">
      <img
        src={blobT}
        sizes="(max-width: 1439px) 93vw, (max-width: 1919px) 75vw, 70vw"
        style={{
          opacity: 1,
          transition: "500ms",
          transform:
            "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg)",
          transformStyle: "preserve-3d",
        }}
        srcSet={`"${blobT500} 500w, ${blobT800} 800w, ${blobT1080} 1080w, ${blobT} 2114w"`}
        alt=""
        className="image blob h-1"
      />
      <img
        src={blobT}
        sizes="(max-width: 1439px) 88vw, (max-width: 1919px) 70vw, (max-width: 3523px) 60vw, 2114px"
        style={{
          opacity: 1,
          transform:
            "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg)",
          transformStyle: "preserve-3d",
        }}
        srcSet={`"${blobT500} 500w, ${blobT800} 800w, ${blobT1080} 1080w, ${blobT} 2114w"`}
        alt=""
        className="image blob h-2"
      />
    </div>
  );
}

export default FullBlobs;
