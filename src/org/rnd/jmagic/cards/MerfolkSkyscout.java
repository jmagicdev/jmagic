package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Merfolk Skyscout")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.MERFOLK})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MerfolkSkyscout extends Card
{
	public static final class CombatUntap extends EventTriggeredAbility
	{
		public CombatUntap(GameState state)
		{
			super(state, "Whenever Merfolk Skyscout attacks or blocks, untap target permanent.");

			SimpleEventPattern attack = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			attack.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(attack);

			SimpleEventPattern block = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			block.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(block);

			Target target = this.addTarget(Permanents.instance(), "target permanent");

			this.addEffect(untap(targetedBy(target), "Untap target permanent."));
		}
	}

	public MerfolkSkyscout(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Merfolk Skyscout attacks or blocks, untap target permanent.
		this.addAbility(new CombatUntap(state));
	}
}
