export function createFooter() {
  const footer = document.createElement('footer');
  footer.className = 'app-footer';
  
  const container = document.createElement('div');
  container.className = 'footer-container';

  // Info Column
  const infoCol = document.createElement('div');
  infoCol.className = 'footer-col';
  infoCol.innerHTML = `
    <h3 class="footer-logo">Woowahan<span class="logo-accent">Delivery</span></h3>
    <p class="footer-text">Delivering the best food from top-rated local restaurants straight to your door.</p>
    <div class="footer-social">
      <a href="#" class="social-link">Instagram</a>
      <a href="#" class="social-link">Twitter</a>
      <a href="#" class="social-link">Facebook</a>
    </div>
  `;

  // Links Column 1
  const linksCol1 = document.createElement('div');
  linksCol1.className = 'footer-col';
  linksCol1.innerHTML = `
    <h4 class="footer-heading">Company</h4>
    <ul class="footer-links">
      <li><a href="#">About Us</a></li>
      <li><a href="#">Careers</a></li>
      <li><a href="#">Blog</a></li>
      <li><a href="#">Partner with Us</a></li>
    </ul>
  `;

  // Links Column 2
  const linksCol2 = document.createElement('div');
  linksCol2.className = 'footer-col';
  linksCol2.innerHTML = `
    <h4 class="footer-heading">Support</h4>
    <ul class="footer-links">
      <li><a href="#">Help Center</a></li>
      <li><a href="#">Terms of Service</a></li>
      <li><a href="#">Privacy Policy</a></li>
      <li><a href="#">Accessibility</a></li>
    </ul>
  `;

  container.appendChild(infoCol);
  container.appendChild(linksCol1);
  container.appendChild(linksCol2);

  const bottom = document.createElement('div');
  bottom.className = 'footer-bottom';
  bottom.innerHTML = `<p>&copy; ${new Date().getFullYear()} Woowahan Delivery. All rights reserved.</p>`;

  footer.appendChild(container);
  footer.appendChild(bottom);
  
  return footer;
}
