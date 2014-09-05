package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Blistercoil Weird")
@Types({Type.CREATURE})
@SubTypes({SubType.WEIRD})
@ManaCost("(U/R)")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class BlistercoilWeird extends Card
{
	public static final class BlistercoilWeirdAbility0 extends EventTriggeredAbility
	{
		public BlistercoilWeirdAbility0(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, Blistercoil Weird gets +1/+1 until end of turn. Untap it.");

			SetGenerator instantOrSorcery = HasType.instance(Type.INSTANT, Type.SORCERY);
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(instantOrSorcery, Spells.instance()));
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Blistercoil Weird gets +1/+1 until end of turn."));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap it."));
		}
	}

	public BlistercoilWeird(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever you cast an instant or sorcery spell, Blistercoil Weird gets
		// +1/+1 until end of turn. Untap it.
		this.addAbility(new BlistercoilWeirdAbility0(state));
	}
}
