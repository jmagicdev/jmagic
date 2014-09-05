package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Deathrender")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class Deathrender extends Card
{
	public static final class DeathrenderAbility0 extends StaticAbility
	{
		public DeathrenderAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +2, +2));
		}
	}

	public static final class DeathrenderAbility1 extends EventTriggeredAbility
	{
		public DeathrenderAbility1(GameState state)
		{
			super(state, "Whenever equipped creature dies, you may put a creature card from your hand onto the battlefield and attach Deathrender to it.");
			this.addPattern(whenXDies(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));

			EventFactory put = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from your hand onto the battlefield");
			put.parameters.put(EventType.Parameter.CAUSE, This.instance());
			put.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			put.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), inYourHand));

			EventFactory attach = attach(ABILITY_SOURCE_OF_THIS, NewObjectOf.instance(EffectResult.instance(put)), "and attach Deathrender to it");

			this.addEffect(youMay(sequence(put, attach), "You may put a creature card from your hand onto the battlefield and attach Deathrender to it."));
		}
	}

	public Deathrender(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2.
		this.addAbility(new DeathrenderAbility0(state));

		// Whenever equipped creature is put into a graveyard, you may put a
		// creature card from your hand onto the battlefield and attach
		// Deathrender to it.
		this.addAbility(new DeathrenderAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
