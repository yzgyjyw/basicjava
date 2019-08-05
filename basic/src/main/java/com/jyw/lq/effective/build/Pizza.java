package com.jyw.lq.effective.build;

public abstract class Pizza {
    /*public enum Topping {HAM, MUSHROOM, ONION}

    final Set<Topping> toppings;

    abstract static Class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
        public T addTopping (Topping topping){
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build ();

        protected abstract T self ();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone(); // See Item 50
    }*/
}

