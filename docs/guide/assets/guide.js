const navLinks = Array.from(document.querySelectorAll('nav a[href^="#"]'));
    const sectionLinks = navLinks
      .map((link) => {
        const section = document.querySelector(link.getAttribute('href'));
        return section ? { link, section } : null;
      })
      .filter(Boolean);

    function setActiveSection(sectionId) {
      navLinks.forEach((link) => {
        const isActive = link.getAttribute('href') === `#${sectionId}`;
        link.classList.toggle('active', isActive);
        link.toggleAttribute('aria-current', isActive);

        const sidebar = link.closest('aside');
        const sidebarCanScroll = sidebar && sidebar.scrollHeight > sidebar.clientHeight;
        const sidebarIsSticky = sidebar && getComputedStyle(sidebar).position === 'sticky';

        if (isActive && sidebarCanScroll && sidebarIsSticky) {
          link.scrollIntoView({ block: 'nearest' });
        }
      });
    }

    function updateActiveSection() {
      const orderedSectionLinks = [...sectionLinks].sort(
        (a, b) => a.section.offsetTop - b.section.offsetTop
      );
      const readingLine = window.scrollY + Math.floor(window.innerHeight * 0.28);
      let currentSectionId = orderedSectionLinks[0]?.section.id;

      orderedSectionLinks.forEach(({ section }) => {
        if (section.offsetTop <= readingLine) {
          currentSectionId = section.id;
        }
      });

      if (currentSectionId) {
        setActiveSection(currentSectionId);
      }
    }

    window.addEventListener('scroll', updateActiveSection, { passive: true });
    window.addEventListener('resize', updateActiveSection);
    updateActiveSection();
