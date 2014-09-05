package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Painsmith")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Painsmith extends Card
{
	public static final class PainsmithAbility0 extends EventTriggeredAbility
	{
		public PainsmithAbility0(GameState state)
		{
			super(state, "Whenever you cast an artifact spell, you may have target creature get +2/+0 and gain deathtouch until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(HasType.instance(Type.ARTIFACT));
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			this.addEffect(youMay(ptChangeAndAbilityUntilEndOfTurn(target, +2, +0, "Target creature gets +2/+0 and deathtouch until end of turn.", org.rnd.jmagic.abilities.keywords.Deathtouch.class), "You may have target creature get +2/+0 and gain deathtouch until end of turn."));
		}
	}

	public Painsmith(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever you cast an artifact spell, you may have target creature get
		// +2/+0 and gain deathtouch until end of turn.
		this.addAbility(new PainsmithAbility0(state));
	}
}
