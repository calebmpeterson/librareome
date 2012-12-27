PDFJS.disableWorker = true;

var pdfDoc = null,
	pageNum = 1,
	scale = 1.0,
	canvas = document.getElementById('pdf-canvas'),
	ctx = canvas.getContext('2d');
	
//
// Get page info from document, resize canvas accordingly, and render page
//
function renderPage(num) {
  // Using promise to fetch the page
  pdfDoc.getPage(num).then(function(page) {
    var viewport = page.getViewport(scale);
    canvas.height = viewport.height;
    canvas.width = viewport.width;

    // Render PDF page into canvas context
    var renderContext = {
      canvasContext: ctx,
      viewport: viewport
    };
    page.render(renderContext);
  });

  // Update page counters
  document.getElementById('page-num').textContent = pageNum;
  document.getElementById('page-count').textContent = pdfDoc.numPages;
}

//
// Go to previous page
//
function goPrevious() {
  if (pageNum <= 1)
    return;
  pageNum--;
  renderPage(pageNum);
}

//
// Go to next page
//
function goNext() {
  if (pageNum >= pdfDoc.numPages)
    return;
  pageNum++;
  renderPage(pageNum);
}

//
// Asynchronously download PDF as an ArrayBuffer
//
PDFJS.getDocument(PDF_URL).then(function getPdfHelloWorld(_pdfDoc) {
  pdfDoc = _pdfDoc;
  renderPage(pageNum);
});
    
   