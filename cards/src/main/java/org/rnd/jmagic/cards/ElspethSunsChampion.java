package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Elspeth, Sun's Champion")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.ELSPETH})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Theros.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class ElspethSunsChampion extends Card
{
	public static final class ElspethSunsChampionAbility0 extends LoyaltyAbility
	{
		public ElspethSunsChampionAbility0(GameState state)
		{
			super(state, +1, "Put three 1/1 white Soldier creature tokens onto the battlefield.");

			CreateTokensFactory soldiers = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Soldier creature tokens onto the battlefield.");
			soldiers.setColors(Color.WHITE);
			soldiers.setSubTypes(SubType.SOLDIER);
			this.addEffect(soldiers.getEventFactory());
		}
	}

	public static final class ElspethSunsChampionAbility1 extends LoyaltyAbility
	{
		public ElspethSunsChampionAbility1(GameState state)
		{
			super(state, -3, "Destroy all creatures with power 4 or greater.");
			this.addEffect(destroy(HasPower.instance(Between.instance(4, null)), "Destroy all creatures with power 4 or greater."));
		}
	}

	public static final class ElspethPump extends StaticAbility
	{
		public ElspethPump(GameState state)
		{
			super(state, "Creatures you control get +2/+2 and have flying.");
			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +2, +2));
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public static final class ElspethSunsChampionAbility2 extends LoyaltyAbility
	{
		public ElspethSunsChampionAbility2(GameState state)
		{
			super(state, -7, "You get an emblem with \"Creatures you control get +2/+2 and have flying.\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"If a source would deal damage to you or a planeswalker you control, prevent all but 1 of that damage.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(ElspethPump.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public ElspethSunsChampion(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Put three 1/1 white Soldier creature tokens onto the battlefield.
		this.addAbility(new ElspethSunsChampionAbility0(state));

		// -3: Destroy all creatures with power 4 or greater.
		this.addAbility(new ElspethSunsChampionAbility1(state));

		// -7: You get an emblem with
		// "Creatures you control get +2/+2 and have flying."
		this.addAbility(new ElspethSunsChampionAbility2(state));
	}
}
