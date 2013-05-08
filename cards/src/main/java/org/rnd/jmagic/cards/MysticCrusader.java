package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mystic Crusader")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MYSTIC, SubType.NOMAD})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class MysticCrusader extends Card
{
	public static final class KingRichard extends StaticAbility
	{
		public KingRichard(GameState state)
		{
			super(state, "As long as seven or more cards are in your graveyard, Mystic Crusader gets +1/+1 and has flying.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public MysticCrusader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(Color.BLACK, Color.RED), "black and from red"));

		this.addAbility(new KingRichard(state));
	}
}
