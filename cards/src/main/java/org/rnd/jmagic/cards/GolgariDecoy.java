package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Golgari Decoy")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.ELF})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class GolgariDecoy extends Card
{
	public static final class GolgariDecoyAbility0 extends StaticAbility
	{
		public GolgariDecoyAbility0(GameState state)
		{
			super(state, "All creatures able to block Golgari Decoy do so.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT_FOR_EACH_DEFENDING_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, HasType.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public GolgariDecoy(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// All creatures able to block Golgari Decoy do so.
		this.addAbility(new GolgariDecoyAbility0(state));

		// Scavenge (3)(G)(G) ((3)(G)(G), Exile this card from your graveyard:
		// Put a number of +1/+1 counters equal to this card's power on target
		// creature. Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(3)(G)(G)"));
	}
}
