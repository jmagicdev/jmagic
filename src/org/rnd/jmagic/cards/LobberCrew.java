package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lobber Crew")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class LobberCrew extends Card
{
	public static final class LobberCrewAbility1 extends ActivatedAbility
	{
		public LobberCrewAbility1(GameState state)
		{
			super(state, "(T): Lobber Crew deals 1 damage to each opponent.");
			this.costsTap = true;
			this.addEffect(permanentDealDamage(1, OpponentsOf.instance(You.instance()), "Lobber Crew deals 1 damage to each opponent."));
		}
	}

	public static final class LobberCrewAbility2 extends EventTriggeredAbility
	{
		public LobberCrewAbility2(GameState state)
		{
			super(state, "Whenever you cast a multicolored spell, untap Lobber Crew.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Multicolored.instance());
			this.addPattern(pattern);

			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Lobber Crew."));
		}
	}

	public LobberCrew(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (T): Lobber Crew deals 1 damage to each opponent.
		this.addAbility(new LobberCrewAbility1(state));

		// Whenever you cast a multicolored spell, untap Lobber Crew.
		this.addAbility(new LobberCrewAbility2(state));
	}
}
