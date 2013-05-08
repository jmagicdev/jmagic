package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ScarsTappedLandAbility extends StaticAbility
{
	private final String permanentName;

	public ScarsTappedLandAbility(GameState state, String permanentName)
	{
		super(state, permanentName + " enters the battlefield tapped unless you control two or fewer other lands.");
		this.permanentName = permanentName;

		ZoneChangeReplacementEffect tapped = new ZoneChangeReplacementEffect(this.game, permanentName + " enters the battlefield tapped");
		tapped.addPattern(asThisEntersTheBattlefield());

		tapped.addEffect(tap(NewObjectOf.instance(tapped.replacedByThis()), permanentName + " enters the battlefield tapped"));
		this.addEffectPart(replacementEffectPart(tapped));
		this.canApply = Intersect.instance(Between.instance(3, null), Count.instance(Intersect.instance(RelativeComplement.instance(HasType.instance(Type.LAND), This.instance()), ControlledBy.instance(You.instance()))));
	}

	@Override
	public ScarsTappedLandAbility create(Game game)
	{
		return new ScarsTappedLandAbility(game.physicalState, this.permanentName);
	}
}