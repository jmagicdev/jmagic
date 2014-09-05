package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jaya Ballard, Task Mage")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPELLSHAPER, SubType.HUMAN})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class JayaBallardTaskMage extends Card
{
	public static final class JayaBallardTaskMageAbility0 extends ActivatedAbility
	{
		public JayaBallardTaskMageAbility0(GameState state)
		{
			super(state, "(R), (T), Discard a card: Destroy target blue permanent.");
			this.setManaCost(new ManaPool("(R)"));
			this.costsTap = true;
			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasColor.instance(Color.BLUE), Permanents.instance()), "target blue permanent"));

			this.addEffect(destroy(target, "Destroy target blue permanent."));
		}
	}

	public static final class JayaBallardTaskMageAbility1 extends ActivatedAbility
	{
		public JayaBallardTaskMageAbility1(GameState state)
		{
			super(state, "(1)(R), (T), Discard a card: Jaya Ballard, Task Mage deals 3 damage to target creature or player. A creature dealt damage this way can't be regenerated this turn.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.costsTap = true;
			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			EventFactory factory = new EventFactory(EventType.DEAL_DAMAGE_EVENLY_CANT_BE_REGENERATED, "Jaya Ballard, Task Mage deals 3 damage to target creature or player. A creature dealt damage this way can't be regenerated this turn.");
			factory.parameters.put(EventType.Parameter.SOURCE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			factory.parameters.put(EventType.Parameter.TAKER, target);
			this.addEffect(factory);
		}
	}

	public static final class JayaBallardTaskMageAbility2 extends ActivatedAbility
	{
		public JayaBallardTaskMageAbility2(GameState state)
		{
			super(state, "(5)(R)(R), (T), Discard a card: Jaya Ballard deals 6 damage to each creature and each player.");
			this.setManaCost(new ManaPool("(5)(R)(R)"));
			this.costsTap = true;
			// Discard a card
			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			this.addEffect(permanentDealDamage(6, CREATURES_AND_PLAYERS, "Jaya Ballard deals 6 damage to each creature and each player."));
		}
	}

	public JayaBallardTaskMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (R), (T), Discard a card: Destroy target blue permanent.
		this.addAbility(new JayaBallardTaskMageAbility0(state));

		// (1)(R), (T), Discard a card: Jaya Ballard, Task Mage deals 3 damage
		// to target creature or player. A creature dealt damage this way can't
		// be regenerated this turn.
		this.addAbility(new JayaBallardTaskMageAbility1(state));

		// (5)(R)(R), (T), Discard a card: Jaya Ballard deals 6 damage to each
		// creature and each player.
		this.addAbility(new JayaBallardTaskMageAbility2(state));
	}
}
