PDFJS.disableWorker = true;

var pdfDoc = null,
	pageNum = INIT_PAGE,
	scale = 1.0,
	canvas = document.getElementById('pdf-canvas'),
	ctx = canvas.getContext('2d');

// Keyboard shortcuts for page navigation	
shortcut.add('N', goNext);
shortcut.add('Right', goNext);
shortcut.add('Page_down', goNext);

shortcut.add('P', goPrevious);
shortcut.add('Left', goPrevious);
shortcut.add('Page_up', goPrevious);
	
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

function recordLatestPage(pageNum) {
  $.ajax({
  	url  : '/read/' + PDF_ID + '/latest',
  	type : 'POST',
  	data : {
  		id   : PDF_ID,
  		page : pageNum
  		}
  	});
}


//
// Zoom in
//
function zoomIn() {
  if (scale > 10.0)
  	return;
  scale += 0.25;
  renderPage(pageNum);
}

//
// Zoom out
//
function zoomOut() {
  if (scale < 1.0)
  	return;
  scale -= 0.25;
  renderPage(pageNum);
}

//
// Go to previous page
//
function goPrevious() {
  if (pageNum <= 1)
    return;
  pageNum--;
  renderPage(pageNum);
  recordLatestPage(pageNum);
}

//
// Go to next page
//
function goNext() {
  if (pageNum >= pdfDoc.numPages)
    return;
  pageNum++;
  renderPage(pageNum);
  recordLatestPage(pageNum);
}

//
// Asynchronously download PDF as an ArrayBuffer
//
PDFJS.getDocument(PDF_URL).then(function getPdfHelloWorld(_pdfDoc) {
  pdfDoc = _pdfDoc;
  $('#pdf-loading').hide();
  $('#pdf-container').show();
  $('#pdf-footer').show();
  renderPage(pageNum);
});
    
   