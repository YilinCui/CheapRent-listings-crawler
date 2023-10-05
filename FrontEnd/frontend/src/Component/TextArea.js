import React from 'react';

function TextArea({ value }) {
  return (
    <textarea readOnly value={value || "No data fetched yet."} />
  );
}

export default TextArea;
