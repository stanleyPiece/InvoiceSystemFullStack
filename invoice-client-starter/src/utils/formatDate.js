export const formatDate = (dateString) => { {/* komponenta pro formátování data */}
    if (!dateString) return "";
    const [year, month, day] = dateString.split('-');
    return `${day}. ${month}. ${year}`;
    };