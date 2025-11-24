const API_BASE = "http://localhost:8080/api";

const elements = {
    apiStatus: document.getElementById("apiStatus"),
    refreshAll: document.getElementById("refreshAll"),
    studentsList: document.getElementById("studentsList"),
    studentByIdResult: document.getElementById("studentByIdResult"),
    createStudentResult: document.getElementById("createStudentResult"),
    assignCourseResult: document.getElementById("assignCourseResult"),
    professorsList: document.getElementById("professorsList"),
    professorByIdResult: document.getElementById("professorByIdResult"),
    createProfessorResult: document.getElementById("createProfessorResult"),
    coursesList: document.getElementById("coursesList"),
    coursesByProfessorResult: document.getElementById("coursesByProfessorResult"),
    createCourseResult: document.getElementById("createCourseResult")
};

const forms = {
    studentById: document.getElementById("studentByIdForm"),
    createStudent: document.getElementById("createStudentForm"),
    assignCourse: document.getElementById("assignCourseForm"),
    professorById: document.getElementById("professorByIdForm"),
    createProfessor: document.getElementById("createProfessorForm"),
    coursesByProfessor: document.getElementById("coursesByProfessorForm"),
    createCourse: document.getElementById("createCourseForm")
};

const buttons = {
    listStudents: document.querySelector('[data-action="listStudents"]'),
    listProfessors: document.querySelector('[data-action="listProfessors"]'),
    listCourses: document.querySelector('[data-action="listCourses"]')
};

const setStatus = (state, message) => {
    const statusEl = elements.apiStatus;
    statusEl.textContent = message;
    statusEl.classList.remove("status--ok", "status--fail", "status--unknown");

    if (state === "ok") statusEl.classList.add("status--ok");
    else if (state === "fail") statusEl.classList.add("status--fail");
    else statusEl.classList.add("status--unknown");
};

const fetchJSON = async (endpoint, options = {}) => {
    const response = await fetch(`${API_BASE}${endpoint}`, {
        headers: { "Content-Type": "application/json", ...(options.headers || {}) },
        ...options
    });

    let payload;
    try {
        payload = await response.json();
    } catch (e) {
        payload = await response.text();
    }

    if (!response.ok) {
        const error = new Error("Error en la petición");
        error.status = response.status;
        error.payload = payload;
        throw error;
    }

    return payload;
};

const renderData = (node, data) => {
    if (!node) return;

    if (data === undefined || data === null) {
        node.innerHTML = "<p class='alert'>Sin datos</p>";
        return;
    }

    if (Array.isArray(data)) {
        if (!data.length) {
            node.innerHTML = "<p class='alert'>Lista vacía</p>";
            return;
        }
        const keys = Array.from(
            data.reduce((acc, item) => {
                if (item && typeof item === "object" && !Array.isArray(item)) {
                    Object.keys(item).forEach((key) => acc.add(key));
                }
                return acc;
            }, new Set())
        );

        node.innerHTML = `
            <table>
                <thead>
                    <tr>${keys.map((key) => `<th>${key}</th>`).join("")}</tr>
                </thead>
                <tbody>
                    ${data
                        .map((item) => {
                            if (!item || typeof item !== "object") {
                                return `<tr><td colspan="${keys.length}">${item}</td></tr>`;
                            }
                            return `<tr>${keys
                                .map((key) => `<td>${formatValue(item[key])}</td>`)
                                .join("")}</tr>`;
                        })
                        .join("")}
                </tbody>
            </table>
        `;
        return;
    }

    if (typeof data === "object") {
        node.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
        return;
    }

    node.textContent = data;
};

const formatValue = (value) => {
    if (value === null || value === undefined) return "—";
    if (typeof value === "object") return JSON.stringify(value);
    return value;
};

const showAlert = (node, message, type = "success") => {
    if (!node) return;
    node.innerHTML = `<p class="alert alert--${type}">${message}</p>`;
};

const listStudents = () =>
    handleAction(elements.studentsList, () => fetchJSON("/estudiantes"));

const listProfessors = () =>
    handleAction(elements.professorsList, () => fetchJSON("/profesores"));

const listCourses = () =>
    handleAction(elements.coursesList, () => fetchJSON("/cursos"));

const handleAction = async (container, action) => {
    if (container) container.innerHTML = "<p>Cargando...</p>";
    try {
        const data = await action();
        renderData(container, data);
        return data;
    } catch (error) {
        const message = error.payload?.message || error.payload || error.message;
        showAlert(container, `${message} (${error.status || "500"})`, "error");
        throw error;
    }
};

const init = () => {
    checkApiStatus();
    attachHandlers();
};

const checkApiStatus = async () => {
    try {
        await fetchJSON("/estudiantes");
        setStatus("ok", "Disponible");
    } catch (error) {
        setStatus("fail", "Sin conexión");
    }
};

const attachHandlers = () => {
    if (buttons.listStudents) buttons.listStudents.addEventListener("click", listStudents);
    if (buttons.listProfessors) buttons.listProfessors.addEventListener("click", listProfessors);
    if (buttons.listCourses) buttons.listCourses.addEventListener("click", listCourses);

    if (elements.refreshAll) {
        elements.refreshAll.addEventListener("click", async () => {
            await Promise.allSettled([listStudents(), listProfessors(), listCourses()]);
        });
    }

    forms.studentById?.addEventListener("submit", async (event) => {
        event.preventDefault();
        const id = event.target.studentId.value.trim();
        if (!id) return;
        await handleAction(elements.studentByIdResult, () =>
            fetchJSON(`/estudiantes/${id}`)
        );
    });

    forms.createStudent?.addEventListener("submit", async (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const payload = {
            nombre: data.get("nombre").trim(),
            email: data.get("email").trim(),
            fechaNacimiento: data.get("fechaNacimiento"),
            detalle: {
                direccion: data.get("direccion").trim(),
                telefono: data.get("telefono").trim()
            }
        };

        try {
            const response = await fetchJSON("/estudiantes", {
                method: "POST",
                body: JSON.stringify(payload)
            });
            renderData(elements.createStudentResult, response);
            showAlert(elements.createStudentResult, "Estudiante creado con éxito");
            event.target.reset();
            await listStudents();
        } catch (error) {
            const message = error.payload?.message || "No se pudo crear el estudiante";
            showAlert(elements.createStudentResult, message, "error");
        }
    });

    forms.assignCourse?.addEventListener("submit", async (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const studentId = data.get("studentId");
        const courseId = data.get("courseId");

        try {
            const response = await fetchJSON(`/estudiantes/${studentId}/cursos/${courseId}`, {
                method: "PUT"
            });
            renderData(elements.assignCourseResult, response);
            showAlert(elements.assignCourseResult, "Curso asignado correctamente");
            await listStudents();
        } catch (error) {
            const message = error.payload?.message || "No se pudo asignar el curso";
            showAlert(elements.assignCourseResult, message, "error");
        }
    });

    forms.professorById?.addEventListener("submit", async (event) => {
        event.preventDefault();
        const id = event.target.professorId.value.trim();
        if (!id) return;
        await handleAction(elements.professorByIdResult, () =>
            fetchJSON(`/profesores/${id}`)
        );
    });

    forms.createProfessor?.addEventListener("submit", async (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const payload = {
            nombre: data.get("nombre").trim(),
            departamento: data.get("departamento").trim()
        };

        try {
            const response = await fetchJSON("/profesores", {
                method: "POST",
                body: JSON.stringify(payload)
            });
            renderData(elements.createProfessorResult, response);
            showAlert(elements.createProfessorResult, "Profesor creado con éxito");
            event.target.reset();
            await listProfessors();
        } catch (error) {
            const message = error.payload?.message || "No se pudo crear el profesor";
            showAlert(elements.createProfessorResult, message, "error");
        }
    });

    forms.coursesByProfessor?.addEventListener("submit", async (event) => {
        event.preventDefault();
        const name = event.target.professorName.value.trim();
        if (!name) return;

        await handleAction(elements.coursesByProfessorResult, () =>
            fetchJSON(`/cursos/buscar?profesor=${encodeURIComponent(name)}`)
        );
    });

    forms.createCourse?.addEventListener("submit", async (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const profesorId = data.get("profesorId");
        const payload = { nombreCurso: data.get("nombreCurso").trim() };

        try {
            const response = await fetchJSON(`/cursos?profesorId=${profesorId}`, {
                method: "POST",
                body: JSON.stringify(payload)
            });
            renderData(elements.createCourseResult, response);
            showAlert(elements.createCourseResult, "Curso creado correctamente");
            event.target.reset();
            await listCourses();
        } catch (error) {
            const message = error.payload?.message || "No se pudo crear el curso";
            showAlert(elements.createCourseResult, message, "error");
        }
    });
};

window.addEventListener("DOMContentLoaded", init);

