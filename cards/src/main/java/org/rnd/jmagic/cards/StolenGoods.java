package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stolen Goods")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class StolenGoods extends Card
{
	public StolenGoods(GameState state)
	{
		super(state);

		// Target opponent exiles cards from the top of his or her library until
		// he or she exiles a nonland card. Until end of turn, you may cast that
		// card without paying its mana cost.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

		SetGenerator library = LibraryOf.instance(target);
		SetGenerator isLand = HasType.instance(Type.LAND);
		SetGenerator toExile = TopMost.instance(library, numberGenerator(1), RelativeComplement.instance(InZone.instance(library), isLand));

		EventFactory exileEffect = exile(toExile, "Target opponent exiles cards from the top of his or her library until he or she exiles a nonland card.");
		this.addEffect(exileEffect);

		SetGenerator mayCast = RelativeComplement.instance(NewObjectOf.instance(EffectResult.instance(exileEffect)), isLand);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_LOCATION);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, mayCast);
		part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));

		this.addEffect(createFloatingEffect("Until end of turn, you may cast that card without paying its mana cost.", part));
	}
}