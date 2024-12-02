import React, { useEffect, useState } from "react";
import notificationService from "../services/notificationService";
import { List, ListItem, ListItemText, Typography } from "@mui/material";

const Notifications = () => {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const userId = localStorage.getItem("userId"); // Récupère l'ID utilisateur si nécessaire
        const data = await notificationService.getNotifications(userId);
        setNotifications(data);
      } catch (error) {
        console.error("Erreur lors de la récupération des notifications :", error);
      }
    };

    fetchNotifications();
  }, []);

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Mes Notifications
      </Typography>
      <List>
        {notifications.map((notification, index) => (
          <ListItem key={index}>
            <ListItemText primary={notification.message} secondary={notification.date} />
          </ListItem>
        ))}
      </List>
    </div>
  );
};

export default Notifications;
