package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nimbus Maze")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class NimbusMaze extends Card
{
	public static final class TapForW extends org.rnd.jmagic.abilities.TapForMana
	{
		public TapForW(GameState state)
		{
			super(state, "(W)");
			this.setName(this.getName() + " Play this ability only if you control an Island.");

			SetGenerator source = ABILITY_SOURCE_OF_THIS;
			SetGenerator heControls = ControlledBy.instance(ControllerOf.instance(source));
			SetGenerator controlsAnIsland = Intersect.instance(HasSubType.instance(SubType.ISLAND), heControls);
			this.addActivateRestriction(Not.instance(controlsAnIsland));
		}
	}

	public static final class TapForU extends org.rnd.jmagic.abilities.TapForMana
	{
		public TapForU(GameState state)
		{
			super(state, "(U)");
			this.setName(this.getName() + " Play this ability only if you control a Plains.");

			SetGenerator source = ABILITY_SOURCE_OF_THIS;
			SetGenerator heControls = ControlledBy.instance(ControllerOf.instance(source));
			SetGenerator controlsAPlains = Intersect.instance(HasSubType.instance(SubType.PLAINS), heControls);
			this.addActivateRestriction(Not.instance(controlsAPlains));
		}
	}

	public NimbusMaze(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
		this.addAbility(new TapForW(state));
		this.addAbility(new TapForU(state));
	}
}
