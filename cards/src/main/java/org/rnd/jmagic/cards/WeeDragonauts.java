package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Wee Dragonauts")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.FAERIE})
@ManaCost("1UR")
@Printings({@Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class WeeDragonauts extends Card
{
	public static final class WeeDragonautsAbility1 extends EventTriggeredAbility
	{
		public WeeDragonautsAbility1(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, Wee Dragonauts gets +2/+0 until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.INSTANT), HasType.instance(Type.SORCERY), Spells.instance()));
			this.addPattern(pattern);

			this.addEffect(createFloatingEffect("Wee Dragonauts gets +2/+0 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +2, +0)));
		}
	}

	public WeeDragonauts(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast an instant or sorcery spell, Wee Dragonauts gets
		// +2/+0 until end of turn.
		this.addAbility(new WeeDragonautsAbility1(state));
	}
}
