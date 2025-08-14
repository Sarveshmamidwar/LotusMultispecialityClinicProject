document.addEventListener("DOMContentLoaded", function () {
    const section = document.querySelector(".doctors-section"); // ✅ Changed selector
    const cards = document.querySelectorAll(".doctor-card");

    // Alternate direction classes
    cards.forEach((card, index) => {
        card.classList.add(index % 2 === 0 ? "from-left" : "from-right");
    });

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                cards.forEach((card, index) => {
                    card.classList.remove("visible");
                    void card.offsetWidth; // force reflow
                    setTimeout(() => {
                        card.classList.add("visible");
                    }, index * 300);
                });
            } else {
                cards.forEach(card => card.classList.remove("visible"));
            }
        });
    }, { threshold: 0.3 });

    observer.observe(section);
});

/* contact section animation  */
	document.addEventListener("DOMContentLoaded", () => {
	    const cards = document.querySelectorAll(".contactbox");

	    const observer = new IntersectionObserver((entries) => {
	        entries.forEach((entry, index) => {
	            if (entry.isIntersecting) {
	                setTimeout(() => {
	                    entry.target.classList.add("show");
	                }, index * 200); // staggered animation
	            } else {
	                // Remove show class when it leaves viewport
	                entry.target.classList.remove("show");
	            }
	        });
	    }, { threshold: 0.3 });

	    cards.forEach(card => observer.observe(card));
	});