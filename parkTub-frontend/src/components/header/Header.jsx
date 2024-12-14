import React from "react";
import { Link } from "react-router-dom";
import styles from "./header.module.css";
import logoImage from "../../assets/images/tub.svg";

const Header = () => {
  return (
    <header className={styles.headerContainer}>
      <div className={styles.logo}>
        <Link to="/">
          <img className={styles["site-logo"]} src={logoImage} />
        </Link>
      </div>

      <nav>
        <ul className={styles.navLinks}>
          <li>
            <Link to="/" className={styles.navLink}>
              Home
            </Link>
          </li>
          <li>
            <Link to="/payments" className={styles.navLink}>
              Pagamentos
            </Link>
          </li>
          <li>
            <Link to="/bonus" className={styles.navLink}>
              Bonificações
            </Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
