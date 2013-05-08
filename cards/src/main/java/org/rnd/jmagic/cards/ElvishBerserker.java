package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Elvish Berserker")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ElvishBerserker extends Card
{
	public static final class Berserker extends EventTriggeredAbility
	{
		public Berserker(GameState state)
		{
			super(state, "Whenever Elvish Berserker becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, thisCard);
			this.addPattern(pattern);

			SetGenerator numCreatureBlockers = Count.instance(Intersect.instance(CreaturePermanents.instance(), EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.DEFENDER)));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, numCreatureBlockers, numCreatureBlockers, "It gets +1/+1 until end of turn for each creature blocking it."));
		}
	}

	public ElvishBerserker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Berserker(state));
	}
}
