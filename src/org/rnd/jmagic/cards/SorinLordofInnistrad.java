package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sorin, Lord of Innistrad")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.SORIN})
@ManaCost("2WB")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class SorinLordofInnistrad extends Card
{
	public static final class SorinLordofInnistradAbility0 extends LoyaltyAbility
	{
		public SorinLordofInnistradAbility0(GameState state)
		{
			super(state, +1, "Put a 1/1 black Vampire creature token with lifelink onto the battlefield.");

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 black Vampire creature token with lifelink onto the battlefield.");
			factory.setColors(Color.BLACK);
			factory.setSubTypes(SubType.VAMPIRE);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Lifelink.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public static final class SorinLordofInnistradAbility1 extends LoyaltyAbility
	{
		public static final class SorinPump extends StaticAbility
		{
			public SorinPump(GameState state)
			{
				super(state, "Creatures you control get +1/+0.");

				this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +1, +0));
			}
		}

		public SorinLordofInnistradAbility1(GameState state)
		{
			super(state, -2, "You get an emblem with \"Creatures you control get +1/+0.\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"Creatures you control get +1/+0.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(SorinPump.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public static final class SorinLordofInnistradAbility2 extends LoyaltyAbility
	{
		public SorinLordofInnistradAbility2(GameState state)
		{
			super(state, -6, "Destroy up to three target creatures and/or other planeswalkers. Return each card put into a graveyard this way to the battlefield under your control.");

			Target target = this.addTarget(Intersect.instance(Permanents.instance(), Union.instance(HasType.instance(Type.CREATURE), RelativeComplement.instance(HasType.instance(Type.PLANESWALKER), ABILITY_SOURCE_OF_THIS))), "up to three target creatures and/or other planeswalkers");
			target.setRange(Between.instance(0, 3));

			SetGenerator allTargets = targetedBy(target);

			EventFactory destroy = destroy(allTargets, "Destroy up to three target creatures and/or other planeswalkers.");
			this.addEffect(destroy);

			SetGenerator movedThisWay = NewObjectOf.instance(EffectZoneChanges.instance(destroy));
			SetGenerator dead = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator diedThisWay = Intersect.instance(dead, movedThisWay);
			this.addEffect(putOntoBattlefield(diedThisWay, "Return each card put into a graveyard this way to the battlefield under your control."));
		}
	}

	public SorinLordofInnistrad(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Put a 1/1 black Vampire creature token with lifelink onto the
		// battlefield.
		this.addAbility(new SorinLordofInnistradAbility0(state));

		// -2: You get an emblem with "Creatures you control get +1/+0."
		this.addAbility(new SorinLordofInnistradAbility1(state));

		// -6: Destroy up to three target creatures and/or other planeswalkers.
		// Return each card put into a graveyard this way to the battlefield
		// under your control.
		this.addAbility(new SorinLordofInnistradAbility2(state));
	}
}
