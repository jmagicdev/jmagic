package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Deathpact Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WBB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class DeathpactAngel extends Card
{
	public static final class DeathpactAngelAbility1 extends EventTriggeredAbility
	{
		public static final class Resurrect extends ActivatedAbility
		{
			public Resurrect(GameState state)
			{
				super(state, "(3)(W)(B)(B), (T), Sacrifice this creature: Return a card named Deathpact Angel from your graveyard to the battlefield.");
				this.setManaCost(new ManaPool("(3)(W)(B)(B)"));
				this.costsTap = true;
				this.addCost(sacrificeThis("this creature"));

				EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Return a card named Deathpact Angel from your graveyard to the battlefield.");
				factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
				factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
				factory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasName.instance("Deathpact Angel"), InZone.instance(GraveyardOf.instance(You.instance()))));
				this.addEffect(factory);
			}
		}

		public DeathpactAngelAbility1(GameState state)
		{
			super(state, "When Deathpact Angel dies, put a 1/1 white and black Cleric creature token onto the battlefield. It has \"(3)(W)(B)(B), (T), Sacrifice this creature: Return a card named Deathpact Angel from your graveyard to the battlefield.\"");
			this.addPattern(whenThisDies());

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white and black Cleric creature token onto the battlefield. It has \"(3)(W)(B)(B), (T), Sacrifice this creature: Return a card named Deathpact Angel from your graveyard to the battlefield.\"");
			factory.setColors(Color.WHITE, Color.BLACK);
			factory.setSubTypes(SubType.CLERIC);
			factory.addAbility(Resurrect.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public DeathpactAngel(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Deathpact Angel dies, put a 1/1 white and black Cleric creature
		// token onto the battlefield. It has "(3)(W)(B)(B), (T), Sacrifice this
		// creature: Return a card named Deathpact Angel from your graveyard to
		// the battlefield."
		this.addAbility(new DeathpactAngelAbility1(state));
	}
}
