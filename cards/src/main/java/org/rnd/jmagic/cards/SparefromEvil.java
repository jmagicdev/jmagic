package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spare from Evil")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SparefromEvil extends Card
{
	public static final class ProtectionNonHumanCreatures extends org.rnd.jmagic.abilities.keywords.Protection
	{
		public ProtectionNonHumanCreatures(GameState state)
		{
			super(state, RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.HUMAN)), "non-Human creatures");
		}
	}

	public SparefromEvil(GameState state)
	{
		super(state);

		// Creatures you control gain protection from non-Human creatures until
		// end of turn.
		this.addEffect(addAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, ProtectionNonHumanCreatures.class, "Creatures you control gain protection from non-Human creatures until end of turn."));
	}
}
