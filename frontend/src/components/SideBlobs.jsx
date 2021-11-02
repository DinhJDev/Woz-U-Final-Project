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

function SideBlobs() {
  return (
    <div className="bg-blobs-wrapper">
      <img
        src={{ blobT }}
        sizes="100vw"
        style={{
          opacity: 1,
          transform:
            "translate3d(0px, 0px, 0px) scale3d(1, 1, 1) rotateX(0deg) rotateY(0deg) rotateZ(0deg) skew(0deg, 0deg); transform-style: preserve-3d",
        }}
        srcSet={`"${blobT500} 500w, ${blobT800} 800w, ${blobT1080} 1080w, ${blobT} 2114w"`}
        alt=""
        className="image blob feature-single-1"
      />
    </div>
  );
}

export default SideBlobs;
