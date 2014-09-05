package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ezuri's Archers")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.ARCHER})
@ManaCost("G")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class EzurisArchers extends Card
{
	public static final class EzurisArchersAbility1 extends EventTriggeredAbility
	{
		public EzurisArchersAbility1(GameState state)
		{
			super(state, "Whenever Ezuri's Archers blocks a creature with flying, Ezuri's Archers gets +3/+0 until end of turn.");
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			pattern.put(EventType.Parameter.ATTACKER, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +3, +0, "Ezuri's Archers gets +3/+0 until end of turn."));
		}
	}

	public EzurisArchers(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Whenever Ezuri's Archers blocks a creature with flying, Ezuri's
		// Archers gets +3/+0 until end of turn.
		this.addAbility(new EzurisArchersAbility1(state));
	}
}
