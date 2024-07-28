const express = require('express');
const app = express();
const port = 3000;
const multer = require('multer');
const path = require('path');
app.get('/ping', (req, res) => {
console.log(req.headers)
  res.json({data: "", message: "pong"});
});


// Set up multer for file uploads
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads/');
    },
    filename: (req, file, cb) => {
        cb(null, file.originalname);
    }
});

const upload = multer({ storage: storage });

// Create the uploads directory if it doesn't exist
const fs = require('fs');
const uploadDir = 'uploads';
if (!fs.existsSync(uploadDir)) {
    fs.mkdirSync(uploadDir);
}

// Define the upload route
app.post('/upload', upload.single('file'), (req, res) => {
    if (!req.file) {
        return res.status(400).send('No file uploaded.');
    }
    res.status(200).send('File uploaded successfully.');
});

app.listen(port, () => {
  console.log(`Ping app listening at http://localhost:${port}`);
});
