package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Root Maze")
@Types({Type.ENCHANTMENT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Tempest.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class RootMaze extends Card
{
	public static final class Maze extends StaticAbility
	{
		public Maze(GameState state)
		{
			super(state, "Artifacts and lands enter the battlefield tapped.");

			ZoneChangeReplacementEffect gatekeeping = new ZoneChangeReplacementEffect(this.game, "Artifacts and lands enter the battlefield tapped");
			gatekeeping.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasType.instance(Type.ARTIFACT, Type.LAND), false));
			gatekeeping.addEffect(tap(NewObjectOf.instance(gatekeeping.replacedByThis()), "An artifact or land enters the battlefield tapped."));

			this.addEffectPart(replacementEffectPart(gatekeeping));
		}
	}

	public RootMaze(GameState state)
	{
		super(state);

		this.addAbility(new Maze(state));
	}
}
