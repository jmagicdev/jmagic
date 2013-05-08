package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hex Parasite")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.INSECT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class HexParasite extends Card
{
	public static final class HexParasiteAbility0 extends ActivatedAbility
	{
		public HexParasiteAbility0(GameState state)
		{
			super(state, "(X)(b/p): Remove up to X counters from target permanent. For each counter removed this way, Hex Parasite gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(X)(b/p)"));

			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			EventFactory factory = new EventFactory(EventType.REMOVE_COUNTERS_CHOICE, "Remove up to X counters from target permanent.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.COUNTER, CountersOn.instance(target));
			factory.parameters.put(EventType.Parameter.NUMBER, Between.instance(Empty.instance(), ValueOfX.instance(This.instance())));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, Count.instance(EffectResult.instance(factory)), numberGenerator(0), "For each counter removed this way, Hex Parasite gets +1/+0 until end of turn."));
		}
	}

	public HexParasite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (X)(b/p): Remove up to X counters from target permanent. For each
		// counter removed this way, Hex Parasite gets +1/+0 until end of turn.
		// ((b/p) can be paid with either (B) or 2 life.)
		this.addAbility(new HexParasiteAbility0(state));
	}
}
